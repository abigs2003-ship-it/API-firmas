/*
====================================================================
SP: SP_ActualizarRutaFirmaTitular
Proposito: Actualiza la columna RutaFirma (varchar/nvarchar) en la
           tabla PMT_App_Ventas_Titulares para el IdTitular indicado.
Parametros:
   @IdTitular  INT            -> identificador del titular
   @RutaFirma  NVARCHAR(500)  -> ruta UNC completa del archivo de imagen
Retorna:
   0  = actualizado correctamente
   -1 = no existe el IdTitular (no se actualizo ningun registro)
====================================================================
*/
IF EXISTS (SELECT 1 FROM sys.objects WHERE type = 'P' AND name = 'SP_ActualizarRutaFirmaTitular')
    DROP PROCEDURE SP_ActualizarRutaFirmaTitular
GO

CREATE PROCEDURE SP_ActualizarRutaFirmaTitular
(
    @IdTitular  INT,
    @RutaFirma  NVARCHAR(500)
)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY

        IF NOT EXISTS (SELECT 1 FROM PMT_App_Ventas_Titulares WHERE IdTitular = @IdTitular)
        BEGIN
            SELECT -1 AS Resultado, 'No existe el titular indicado' AS Mensaje;
            RETURN;
        END

        UPDATE PMT_App_Ventas_Titulares
           SET RutaFirma = @RutaFirma
         WHERE IdTitular = @IdTitular;

        SELECT 0 AS Resultado, 'Actualizado correctamente' AS Mensaje;

    END TRY
    BEGIN CATCH
        SELECT -2 AS Resultado, ERROR_MESSAGE() AS Mensaje;
    END CATCH
END
GO
