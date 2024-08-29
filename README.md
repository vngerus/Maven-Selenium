
# Maven-Selenium Project

## Índice
1. [Introducción](#introducción)
    - [Objetivos](#objetivos)
2. [Configuración del Entorno](#configuración-del-entorno)
    - [Requisitos](#requisitos)
    - [Variables de Entorno](#variables-de-entorno)
    - [Instalación](#instalación)
3. [Estructura del Proyecto](#estructura-del-proyecto)
    - [Arquitectura](#arquitectura)
    - [Estructura de Carpetas](#estructura-de-carpetas)
4. [Pipeline de Jenkins](#pipeline-de-jenkins)
    - [Descripción del Pipeline](#descripción-del-pipeline)
    - [Estructura del Pipeline](#estructura-del-pipeline)
    - [Configuración del Pipeline en Jenkins](#configuración-del-pipeline-en-jenkins)
    - [Resultados y Logs](#resultados-y-logs)
5. [Uso y Mantenimiento](#uso-y-mantenimiento)
    - [Actualización de Dependencias](#actualización-de-dependencias)
    - [Solución de Problemas](#solución-de-problemas)
6. [Conclusiones y Próximos Pasos](#conclusiones-y-próximos-pasos)
7. [Referencias](#referencias)
8. [Anexos](#anexos)
    - [Archivos de Configuración](#archivos-de-configuración)
    - [Scripts Utilizados](#scripts-utilizados)

---

## Introducción
Este proyecto utiliza HTML, CSS, JavaScript, y Java para automatizar pruebas utilizando Selenium. Está configurado para ejecutarse en un entorno de integración continua con Jenkins.

**Repositorio:** [Maven-Selenium](https://github.com/vngerus/Maven-Selenium)

### Objetivos
- Automatizar pruebas de la interfaz de usuario y funcionalidades del proyecto web.
- Integración continua utilizando Jenkins para la compilación, pruebas y notificaciones.

## Configuración del Entorno
### Requisitos
- **Java JDK:** Version 11 o superior.
- **Maven:** Version 3.6.0 o superior.
- **Selenium:** Integrado como dependencia de Maven.
- **Navegador Web:** Compatible con Selenium WebDriver (ChromeDriver configurado en el proyecto).

### Variables de Entorno
- `JAVA_HOME`: Ruta al JDK de Java.
- `MAVEN_HOME`: Ruta de Maven.
- `CHROME_DRIVER_PATH`: Ruta al ejecutable de ChromeDriver.

### Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/vngerus/Maven-Selenium.git
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd Maven-Selenium
   ```
3. Instala las dependencias utilizando Maven:
   ```bash
   mvn clean install
   ```

## Estructura del Proyecto
### Arquitectura
- **Front-End:** HTML, CSS, y JavaScript.
- **Back-End:** Java con Selenium para pruebas automatizadas.

### Estructura de Carpetas
- `src/main/java`: Código fuente principal.
- `src/test/java`: Código de pruebas.
- `src/main/resources`: Archivos de configuración y recursos.

## Pipeline de Jenkins
### Descripción del Pipeline
El pipeline está configurado para compilar y probar el proyecto automáticamente, enviar notificaciones a Slack y limpiar el espacio de trabajo al finalizar.

### Estructura del Pipeline
1. **Preparación del Entorno:**
   - Configuración de variables de entorno y selección de Maven.
2. **Compilación del Proyecto:**
   - Ejecuta `mvn clean install` para compilar y generar el JAR.
3. **Ejecución de Pruebas:**
   - Ejecuta `mvn test` para correr las pruebas automatizadas.
4. **Notificación a Slack:**
   - Envío de notificación al canal `#time-tracker-ci`.
5. **Limpieza del Espacio de Trabajo:**
   - Limpia el espacio de trabajo para evitar acumulaciones.

### Configuración del Pipeline en Jenkins
Ejemplo del archivo `Jenkinsfile`:

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                withEnv(["MAVEN_HOME=${tool 'M3'}", "PATH+MAVEN=${MAVEN_HOME}/bin"]) {
                    bat "mvn clean install"
                }
            }
        }
        stage('Test') {
            steps {
                withEnv(["MAVEN_HOME=${tool 'M3'}", "PATH+MAVEN=${MAVEN_HOME}/bin"]) {
                    bat "mvn test"
                }
            }
        }
        stage('Notify') {
            steps {
                slackSend channel: '#time-tracker-ci', message: 'Build and Tests Completed Successfully', iconEmoji: '👌', username: 'El viejo jenkins'
            }
        }
        stage('Cleanup') {
            steps {
                cleanWs()
            }
        }
    }
}
```

### Resultados y Logs
- Dependencias descargadas durante la ejecución del pipeline:
  ```bash
  Downloaded from central: https://repo.maven.apache.org/maven2/org/codehaus/plexus/plexus-digest/1.0/plexus-digest-1.0.jar (12 kB at 539 kB/s)
  Downloaded from central: https://repo.maven.apache.org/maven2/org/codehaus/plexus/plexus-utils/3.0.5/plexus-utils-3.0.5.jar (230 kB at 9.6 MB/s)
  ```
- Ejemplo de resultado de una prueba exitosa:
  ```bash
  [INFO] BUILD SUCCESS
  [INFO] Total time:  01:12 min
  [INFO] Finished at: 2024-08-28T20:30:21-04:00
  ```

## Uso y Mantenimiento
### Actualización de Dependencias
Para actualizar las dependencias, edita el archivo `pom.xml` y ejecuta:
```bash
mvn clean install
```

### Solución de Problemas
- **Errores de Compilación:** Verifica las versiones de Java y Maven.
- **Problemas con ChromeDriver:** Asegúrate de que `CHROME_DRIVER_PATH` esté correctamente configurado.

## Conclusiones y Próximos Pasos
El pipeline automatiza la compilación, pruebas y notificaciones, asegurando un entorno limpio y eficiente para cada ejecución. Los próximos pasos incluyen la integración de más pruebas y la mejora continua del proyecto.

## Referencias
- **Selenium Documentation:** https://www.selenium.dev/documentation/en/
- **Maven Documentation:** https://maven.apache.org/guides/index.html
- **Jenkins Documentation:** https://www.jenkins.io/doc/

## Anexos
### Archivos de Configuración
- `pom.xml`: Archivo de configuración de Maven.
- `Jenkinsfile`: Archivo de configuración del pipeline en Jenkins.

### Scripts Utilizados
Listado y descripción de scripts personalizados, si aplica.

---

_Este archivo README proporciona toda la información necesaria para configurar, ejecutar y mantener el proyecto Maven-Selenium._
