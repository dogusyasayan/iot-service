![image](https://github.com/user-attachments/assets/221f8798-beb5-44eb-b70b-b2c3572fcf38)

# 🚀 IoT Asset Management Service (OpenRemote Integration)

Bu proje, **OpenRemote platformu** ile entegre çalışan, **IoT cihazlarının varlık (asset) yönetimini** sağlayan bir mikroservistir. Spring Boot altyapısında geliştirilen bu servis, OpenRemote’un REST API’lerine bağlanarak **asset oluşturma, güncelleme, silme ve listeleme** işlemlerini gerçekleştirebilir.

Servis; güvenli token yapısı, modüler tasarım, attribute strategy pattern kullanımı ve Docker destekli mimarisi ile güçlü bir entegrasyon sunar.

---

## ⚙️ Kullanılan Teknolojiler

| Teknoloji | Açıklama |
|----------|----------|
| Java 21 | Modern ve güçlü dil özellikleri |
| Spring Boot 3.2 | Mikroservis çatısı |
| Spring Cloud OpenFeign | REST API iletişimi |
| WebClient | Token alma ve dış API çağrıları |
| Docker & Docker Compose | Container yönetimi |
| Swagger UI | API dökümantasyonu |
| JUnit 5 & Mockito | Unit test altyapısı |
| Lombok | Boilerplate azaltma |

---

## 🧠 Sistem Mimarisi

```text
               +-----------------------+
               |    Keycloak (Auth)   |
               +-----------------------+
                         |
                         ↓
+------------+     Token Request     +---------------------+
|  IoT       |---------------------->| OpenRemote Token API|
|  Service   |   Bearer Token ile    +---------------------+
| (Spring)   |<----------------------| Access Token         |
+------------+                       +---------------------+
     |
     | Asset Request (FeignClient)
     ↓
+----------------------+
|  OpenRemote Asset API|
+----------------------+

---
```

## 🔐 Yetkilendirme

Bu servis, OpenRemote'un Keycloak servisi üzerinden `client_credentials` akışıyla token alır ve tüm API çağrılarını bu **Bearer Token** ile yapar.

```http
POST /auth/token
Authorization: Basic base64(client_id:secret)

Response:
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiS...",
  "token_type": "Bearer",
  "expires_in": 3600
}

---
```
## 📦 API Endpointleri

| Endpoint                     | Açıklama                                  |
|-----------------------------|--------------------------------------------|
| `POST /api/assets`          | Yeni bir asset oluşturur                   |
| `GET /api/assets/{id}`      | Belirli bir asset'i görüntüler             |
| `PUT /api/assets/{id}`      | Asset bilgilerini günceller                |
| `DELETE /api/assets/{id}`   | Asset'i siler                              |
| `GET /api/assets/links`     | Kullanıcıya bağlı tüm asset'leri listeler  |

---

### 🧪 Testler

- Unit testler `JUnit 5` ve `Mockito` ile yazıldı.
- `@WebMvcTest`, `@MockBean`, `@DataJpaTest` gibi Spring test araçları kullanıldı.
- `Test Coverage` yüksek tutuldu.

```bash
```
### 📚 Swagger UI

> Swagger arayüzü üzerinden tüm endpointleri test edebilirsiniz.

**URL:**  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

### 🐳 Docker Kullanımı

### 1. Jar oluştur
```bash
mvn clean package -DskipTests
```
### 2. Docker image oluştur
```bash
docker build -t iot-service .
```
### 2. Docker Compose ile başlat
```bash
docker-compose up --build
```
### 📂 Proje Yapısı
```text
iot-service/
├── src/
│   ├── main/
│   │   ├── java/com/iot/...
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

### 🧰 Önemli Özellikler
```text
✅ OpenRemote ile güvenli token tabanlı iletişim
✅ FeignClient ve WebClient kombinasyonu
✅ Swagger UI üzerinden dokümantasyon ve test
✅ Oop prensiplerine uygun katmanlı mimari
✅ Clean Code prensiplerine uygun yapı
```

---

### 🔍 Docker Port Health Check

Aşağıda, Docker container'larının sağlıklı şekilde ayağa kalktığını ve port yönlendirmelerinin düzgün çalıştığını gösteren kontrol ekranı yer almaktadır:

<img width="862" alt="Docker Health" src="https://github.com/user-attachments/assets/6a43172e-becb-4f67-b4bf-1130538ff6d9" />

---

### 🔐 OpenRemote Service User Configuration

OpenRemote arayüzü üzerinde servis kullanıcısı başarıyla tanımlandı. Bu kullanıcı ile `client_credentials` üzerinden token alınmakta ve sistemle güvenli şekilde haberleşilmektedir.

<img width="1317" alt="Service User Config" src="https://github.com/user-attachments/assets/7d8de02d-2e9a-402d-a1cc-33e83f40e10e" />

---

### 📤 OpenRemote Asset Oluşturma – Örnek POST İsteği

Swagger UI aracılığıyla asset oluşturmak için gönderilen örnek isteğe ait ekran görüntüsü aşağıdadır. Bu istek ile OpenRemote içinde başarılı şekilde varlık kaydı yapılmıştır.

<img width="956" alt="image" src="https://github.com/user-attachments/assets/9eb08025-48a9-4e90-aa45-ab5ccad7aa41" />


---

### 🧾 OpenRemote Üzerinde Asset Görüntüleme

Yukarıda oluşturulan asset, OpenRemote arayüzünde aşağıdaki gibi görüntülenebilmekte ve kontrol edilebilmektedir:

<img width="1294" alt="image" src="https://github.com/user-attachments/assets/5cfe5462-cf05-4e2f-8c67-2dcb737d7368" />


---

### 📚 Swagger UI & API Test Arayüzü

Tüm endpoint'ler Swagger UI arayüzü üzerinden görüntülenebilir ve token ile test edilebilir. Geliştiriciler için API dökümantasyonu ve kolay test imkanı sağlar.

![image](https://github.com/user-attachments/assets/f30206dd-fcd4-4eaa-8885-196e6c96dcd3)


---



