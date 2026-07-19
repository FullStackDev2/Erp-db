# ERP Project

Kurumsal Kaynak Planlama (ERP) sistemi - Java/Spring Boot + PostgreSQL + REST + SOAP

## 🚀 Canlı Demo

- **API:** https://erp-project-7q64.onrender.com
- **Swagger UI:** https://erp-project-7q64.onrender.com/swagger-ui/index.html
- **SOAP WSDL:** https://erp-project-7q64.onrender.com/ws/products.wsdl

> Not: Render'ın ücretsiz planında servis bir süre trafik almazsa uyku moduna geçer, ilk istek 30-60 saniye sürebilir.

## Modüller

- **user** — kullanıcı yönetimi & JWT auth
- **inventory** — stok/ürün yönetimi (REST + öğrenme amaçlı SOAP endpoint)
- **sales** — satış modülü (henüz boş, iskelet hazır)
- **finance** — muhasebe modülü (henüz boş, iskelet hazır)

## Gereksinimler

- Java 21
- Maven 3.9+
- PostgreSQL 15+

## Kurulum

1. PostgreSQL'de veritabanı oluştur:

```sql
CREATE DATABASE erp_db;
CREATE USER erp_user WITH PASSWORD 'erp_password';
GRANT ALL PRIVILEGES ON DATABASE erp_db TO erp_user;
```

2. `src/main/resources/application-dev.yml` dosyasındaki bağlantı bilgilerini kendine göre düzenle.

3. Projeyi derle ve çalıştır:

```bash
mvn clean install
mvn spring-boot:run
```

4. Flyway migration'ları otomatik çalışır (`src/main/resources/db/migration/`).

## Endpoint'ler

- REST API: `http://localhost:8080/api/v1/products`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- SOAP WSDL: `http://localhost:8080/ws/products.wsdl`

## Notlar

- SOAP tarafı öğrenme amaçlı kurulmuştur (`ProductEndpoint`). JAXB sınıfları
  `products.xsd`'den `mvn generate-sources` ile otomatik üretilir.
- Şema değişiklikleri Flyway migration dosyalarıyla yönetilir, `ddl-auto: validate`
  olduğu için Hibernate şema oluşturmaz, sadece doğrular.
