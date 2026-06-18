package com.contratos.API.Firmas.service;

import com.contratos.API.Firmas.exception.FirmaTitularException;
import com.contratos.API.Firmas.model.FotoTitularRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
public class FotoTitularService {

    private static final Logger log = LoggerFactory.getLogger(FotoTitularService.class);

    private static final Set<String> EXTENSIONES_PERMITIDAS = Set.of("jpg", "jpeg", "png");

    private final JdbcClient jdbcClient;

    @Value("${app.storage.ruta-compartida}")
    private String rutaCompartida;

    public FotoTitularService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Decodifica la imagen Base64, la guarda en la carpeta compartida
     * (nombrada solo con el Nombre del Titular, sobrescribiendo si ya existe) y
     * registra la ruta resultante en la BD a traves del SP.
     *
     * @return la ruta UNC final guardada en la BD
     */
    public String guardarFotoYRegistrar(FotoTitularRequest request) {
        validarExtension(request.getExtension());

        byte[] bytesImagen = decodificarBase64(request.getImagenBase64());

        Path rutaArchivo = guardarArchivoEnCarpeta(request.getNombreCompleto(), bytesImagen, request.getExtension());

        actualizarRutaEnBD(request.getIdTitular(), rutaArchivo.toString());

        return rutaArchivo.toString();
    }

    private void validarExtension(String extension) {
        if (extension == null || !EXTENSIONES_PERMITIDAS.contains(extension.toLowerCase())) {
            throw new FirmaTitularException("Extension no permitida: " + extension);
        }
    }

    private byte[] decodificarBase64(String base64) {
        try {
       
            String limpio = base64.contains(",") ? base64.substring(base64.indexOf(',') + 1) : base64;
            return Base64.getDecoder().decode(limpio);
        } catch (IllegalArgumentException ex) {
            throw new FirmaTitularException("La cadena Base64 de la imagen es invalida");
        }
    }

    private Path guardarArchivoEnCarpeta(String nombreCompleto, byte[] bytesImagen, String extension) {
        try {
            Path carpeta = Paths.get(rutaCompartida);

            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            // Nombre =nombreCompleto titular
            String nombreArchivo = nombreCompleto + "." + extension.toLowerCase();
            Path rutaFinal = carpeta.resolve(nombreArchivo);

            Files.write(rutaFinal, bytesImagen);

            log.info("Imagen guardada en {}", rutaFinal);
            return rutaFinal;

        } catch (IOException ex) {
            log.error("Error escribiendo la imagen en la carpeta compartida", ex);
            throw new FirmaTitularException(
                    "No se pudo guardar la imagen en la carpeta compartida. Verifique la ruta y los permisos.", ex);
        }
    }

    private void actualizarRutaEnBD(Integer idTitular, String rutaFirma) {
        try {
            List<java.util.Map<String, Object>> resultado = jdbcClient
                    .sql("EXEC SP_ActualizarRutaFirmaTitular :idTitular, :rutaFirma")
                    .param("idTitular", idTitular, Types.INTEGER)
                    .param("rutaFirma", rutaFirma, Types.NVARCHAR)
                    .query()
                    .listOfRows();

            if (resultado.isEmpty()) {
                throw new FirmaTitularException("El SP no devolvio resultado");
            }

            Number codigoResultado = (Number) resultado.get(0).get("Resultado");
            String mensaje = String.valueOf(resultado.get(0).get("Mensaje"));

            if (codigoResultado.intValue() != 0) {
                throw new FirmaTitularException("Error del SP: " + mensaje);
            }

        } catch (FirmaTitularException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error ejecutando SP_ActualizarRutaFirmaTitular", ex);
            throw new FirmaTitularException("Error al actualizar la ruta en base de datos", ex);
        }
    }
}
