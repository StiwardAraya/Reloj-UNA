# RelojUNA — Sistema de Control de Asistencia Empresarial

> Aplicación de escritorio tipo "reloj marcador" desarrollada en **JavaFX** para gestionar la entrada y salida de empleados, calcular planillas de pago con reglas laborales complejas (horas ordinarias, extras, dobles y nocturnas) y generar reportes en Excel y JasperReports, todo comunicado mediante **Web Services SOAP** contra un backend con **JPA/Oracle**.

Proyecto académico desarrollado para el curso **Programación III**, Ingeniería en Sistemas de Información, Universidad Nacional de Costa Rica (Sede Regional Brunca).

---

## Descripción general

RelojUNA simula el sistema de marcaje de una empresa real: los colaboradores registran su entrada y salida mediante un folio, un administrador gestiona empleados y corrige inconsistencias, y el sistema calcula automáticamente las planillas mensuales aplicando reglas de jornada diurna/nocturna, horas extra y pago doble en días libres.

El proyecto se construyó como una arquitectura **cliente-servidor**: un cliente de escritorio en JavaFX que consume una capa de servicios web SOAP, la cual a su vez persiste la información en una base de datos Oracle mediante JPA.

## Funcionalidades principales

- **Autenticación de administradores** con pantalla de LogIn y menú principal de navegación.
- **Mantenimiento de empleados**: registro de datos personales, foto almacenada en base de datos, folio único, salario por hora y credenciales de acceso administrativo.
- **Pantalla de marcaje** con reloj digital en tiempo real, confirmación visual (nombre, foto y hora) al marcar, y animación personalizada de felicitación en cumpleaños. Accesible tanto desde el sistema como desde un acceso directo independiente para uso general de los empleados.
- **Detección automática de nuevas jornadas** cuando transcurren más de 16 horas entre marcas.
- **Mantenimiento de marcas** en formato tabla, con edición, eliminación y creación manual de registros por fecha.
- **Detección de inconsistencias**: identifica marcas incompletas (sin entrada o salida), las resalta visualmente, muestra un contador y permite navegar directamente entre ellas.
- **Generación de planillas mensuales**, calculando horas ordinarias, extras, dobles y nocturnas según reglas de negocio configurables (jornada diurna 2am–10pm, redondeo a 30 minutos, recargo de 1.3333 en jornada nocturna, 1.5 en horas extra y doble pago en día libre).
- **Módulo de consultas** de marcas por rango de fechas y por empleado (o todos), con totalización de horas y cantidad de empleados/marcas, implementado íntegramente con **Java Streams**.
- **Exportación a Excel con formato**, generada 100% desde código Java (sin plantillas ni CSV).
- **Reportes en JasperReports** de información de empleados y de horas trabajadas por rango de fechas, con totales individuales y generales.

## Stack tecnológico

| Categoría | Tecnología |
|---|---|
| Cliente de escritorio | Java, JavaFX 26, CSS |
| Comunicación cliente-servidor | Web Services SOAP |
| Persistencia | JPA (Jakarta Persistence) |
| Base de datos | Oracle Database |
| Pool de conexiones | `relojPool` / recurso JDBC `jdbc/Reloj` |
| Reportes | JasperReports |
| Exportación de datos | Apache POI (Excel generado por código) |
| Procesamiento de datos | Java Streams, JPQL |
| Modelado de datos | Toad Data Modeler |

## Aprendizajes y objetivos técnicos

Este proyecto fue diseñado para poner en práctica e integrar en un solo sistema:

- Desarrollo de interfaces de escritorio con **JavaFX** y estilos **CSS**.
- Diseño orientado a objetos con **clases e interfaces**.
- Procesamiento funcional de colecciones con **Streams** para reportes y consultas.
- Consumo e implementación de **Web Services SOAP**.
- Persistencia de datos con **JPA** sobre Oracle.
- Generación de archivos **Excel** desde código y reportes profesionales con **JasperReports**.
- Modelado de bases de datos con herramientas CASE (**Toad Data Modeler**).
- Implementación de reglas de negocio reales de cálculo de planillas (jornadas diurnas/nocturnas, horas extra y dobles).

## Autores

Proyecto desarrollado en equipo de 3 estudiantes — Ingeniería en Sistemas de Información, UNA Sede Regional Brunca.

- Stiward Araya
- Andres Tames
- Isaac Picado

## Contexto académico

- **Curso:** Programación III
- **Profesor:** Máster Carlos Carranza Blanco
- **Institución:** Universidad Nacional de Costa Rica, Sede Regional Brunca
- **Valor de la tarea:** 20% de la nota del curso

---

*Este README fue elaborado como documentación de portafolio para destacar las tecnologías y competencias aplicadas en el proyecto.*
