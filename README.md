# Back-End E-Commerce - Prueba Técnica

## Descripción
API REST para gestión de pedidos con Spring Boot, JPA y PostgreSQL. Incluye CRUD, líneas de pedido, cambio de estado y filtros con paginación.

## Requisitos
- Java 21
- Maven 3.8+
- PostgreSQL 16

## Instalación

1. **Clonar repositorio**
```bash
git clone https://github.com/GregoryG8/back-SemiSenior.git
cd back-SemiSenior
```

2. **Configurar base de datos** (`src/main/resources/application.properties`)
```properties
SUPABASE_URL="jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?sslmode=require";
SUPABASE_PASSWORD=Greekodata8@;
SUPABASE_USERNAME=postgres.mdiqrbjvrdowsqdihuva;
SPRING_PROFILES_ACTIVE=supabase
```

3. **Ejecutar**
```bash
mvn spring-boot:run
```

## Puerto por Defecto
**`http://localhost:8080`** (puerto 8080)

## Documentacion API (OpenAPI 3)

Esta API incluye documentacion interactiva con SpringDoc.

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Autenticacion en Swagger

Si la seguridad esta habilitada, utiliza las credenciales por defecto:

- Usuario: `demo`
- Password: `demo`

En Swagger UI, presiona **Authorize** y selecciona autenticacion HTTP Basic.
