# 🚀 Challenge Backend API - Spring Boot

Este proyecto implementa una API REST con Java 21 y Spring Boot que permite sumar dos números y aplicar un porcentaje obtenido de un servicio externo. La aplicación cachea ese porcentaje, registra el historial de llamadas en PostgreSQL y se ejecuta usando Docker Compose.

---

## ✅ Funcionalidades

- ✅ Cálculo dinámico: (num1 + num2) * (1 + porcentaje)
- ✅ Cache del porcentaje por 30 minutos
- ✅ Fallback si el servicio externo falla
- ✅ Registro asíncrono de historial (endpoint, parámetros, respuesta/error)
- ✅ Endpoint para consultar historial con paginación
- ✅ Documentación Swagger
- ✅ Tests unitarios con JUnit y Mockito
- ✅ Dockerizado

---

## 🧰 Tecnologías

- Java 21
- Spring Boot 3
- PostgreSQL
- Spring Cache
- Spring Data JPA
- Docker + Docker Compose
- Springdoc OpenAPI (Swagger)
- JUnit 5, Mockito

---

## 📦 Cómo ejecutar el proyecto paso a paso

### 🔹 Paso 1: Clonar el repositorio

```bash
git clone https://github.com/msalinas-se/tenpo-challenge.git
cd tenpo-challenge
```

### 🔹 Paso 2: Compilar la aplicación

```bash
./mvnw clean package -DskipTests
```

### 🔹 Paso 3: Levantar los servicios con Docker

```bash
docker-compose up --build
```

Esto iniciará:
- La API en http://localhost:8080
- PostgreSQL en el puerto 5432, con base de datos challenge, usuario postgres, contraseña postgres.

### 🔹 Paso 4: Acceder a la documentación Swagger

Abre el navegador en:

http://localhost:8080/swagger-ui.html

Desde ahí puedes probar los endpoints como:
- POST /api/calculate
- GET /api/history


