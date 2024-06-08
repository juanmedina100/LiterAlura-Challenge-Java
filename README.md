# LiterAlura Challenge Java

## Challenge Backend de Alura

#### Buscador de libros consumiendo la API de la web [Gutendex](https://gutendex.com/) para obtener toda la información de los libros y posteriormente guardarlos en una base de datos de PosgrestSQL.

Ademas el consumo de los [lenguajes](https://juanmedina100.github.io/idiomas-iso-639-1-json/idiomas-639-1.json) para obtener su nombre de idioma partiendo de su código para poder enrriquecer aun mas la aplicación.

## Requesitos para descarga y uso de LiterAlura Challenge Java

    - Clonar o descargar el repositorio.
    - Instalar PostgreSQL (O usar Docker)
    - Configurar las variables de entorno.

## Variables de entorno

Crear un archivo en src/main/resources/ llamada : env.properties y agrega las siguientes varibles:

| VARIABLE | DESCRIPCIÓN                                                                                                                 |
|----------|-----------------------------------------------------------------------------------------------------------------------------|
| DB_HOST | Si se esta trabajando en un entorno local debe ser: localhost en caso de ser un servicor en linea debe ser: tu dirección IP |
| DB_PORT | Este es el puerto donde esta corriendo nuestra base de datos                                                                |
| DB_BOOKS | Este es el nombre de tu base de datos                                                                                       |
| USER_NAME | Este es el nombre de usuario para conectarnos a la base de datos                                                            |
| PASSWORD | Este es la contraseña de la base de datos                                                                                   |


# Aplicación

## Menú

#### A continuación el menú de la aplicaión en el cual se muestran los campos requeridos y los campos adicionales.

![](assets/img01.jpg)
#### LiterAlura Challenge solicitado nos solicita 6 funcionalidades en la aplicación que son las que estan marcadas con color azul + la opción 0 que es para cerrar la aplicación.
#### En este proyecto se han agregado 4 opción más con el fin de demostrar los conocimientos adquirido en clases de Alura.

## ¿Como funciona esta aplicación?

### General:
#### Una vez configurada la aplicación correr la aplicación (dando clic en Run) lo cual nos cargara el menu principal

![](assets/img02.jpg)

#### Nos solicitara que elijamos una opción de 1 al 9 o la opción 0 para salir.

#### Al presionar el numero 1 nos pedira el titulo del libro 

![](assets/img03.jpg)

### 1 - Buscar libro por titulo
#### Esta opción buscara en la API de [Gutendex](https://gutendex.com/) para mostrarlo en panatalla y simultaneamente lo guardara en la base de datos previamente creada.
#### En esta opción hay dos tipos de respuesta 

#### - Libro encontrado



#### - libro no encontrado



