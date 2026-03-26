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
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=--Contraseña--
spring.jpa.hibernate.ddl-auto=update
```

3. **Ejecutar**
```bash
mvn spring-boot:run
```

## Puerto por Defecto
**`http://localhost:8080`** (puerto 8080)