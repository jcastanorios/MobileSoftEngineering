# MISW4203-202415
Espacio de trabajo del grupo 10

## Requerimientos
Este proyecto se conecta al back en la dirección:
https://backvynils-q6yc.onrender.com/
Se necesita que el back esté corriendo para que la app funcione correctamente.
 
## Pasos para constriur y correr la app

1. Clonar el repositorio
2. Abrir el proyecto en Android Studio
3. Esperar a que se sincronizen las dependencias con Gradle (Si es la primera vez que se abre el proyecto)
    - Si no es la primera vez, sincronizar las dependencias con Gradle manualmente
4. Correr la app (^R)

## Ejecución de pruebas de UI con Espresso

### Requisitos previos:

Asegúrate de que el proyecto esté abierto en Android Studio y que las dependencias estén completamente sincronizadas.

### Pasos para ejecutar las pruebas:

1. Navega al archivo de prueba en el explorador de archivos de Android Studio. En este caso, ve a:

   `app/src/androidTest/java/com.vinylsmobile/AlbumListTest.java`
   `app/src/androidTest/java/com.vinylsmobile/AlbumDetailTest.java`
   `app/src/androidTest/java/com.vinylsmobile/PerformerListTest.java`
   `app/src/androidTest/java/com.vinylsmobile/ArtistDetailTest.java`
   `app/src/androidTest/java/com.vinylsmobile/CollectorListTest.java`
   `app/src/androidTest/java/com.vinylsmobile/CollectorDetailTest.java`
   `app/src/androidTest/java/com.vinylsmobile/CommentAlbum.java`
   `app/src/androidTest/java/com.vinylsmobile/CreateAlbum.java`
 
 
2. Haz clic derecho sobre el archivo a ejecutar y selecciona Run ‘NombreDelArchivo’ para ejecutar todas las pruebas en la clase.
   - Para ejecutar una prueba específica, abre el archivo y haz clic en el ícono de ejecución (▶) junto al nombre de la prueba.