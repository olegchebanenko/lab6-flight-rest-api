# Лабораторна робота № 6 — Flight REST API

Java 21, Spring Boot 3.5.7, Gradle Groovy, Spring Web.

## Запуск
1. На https://start.spring.io створіть Gradle-Groovy / Java / Jar / Java 21 проєкт: Group `com.example`, Artifact `flight-api`, Package name `com.example.flightapi`, Spring Web. Якщо 3.5.7 відсутня, оберіть доступну версію 3.5.x (або іншу доступну стабільну версію лише для отримання wrapper).
2. Розпакуйте проєкт, накладіть вміст цього архіву поверх нього. Збережіть `gradlew`, `gradlew.bat` та `gradle/wrapper/`. У `gradle/wrapper/gradle-wrapper.properties` встановіть `distributionUrl=https\://services.gradle.org/distributions/gradle-8.14.3-bin.zip`. Залиште лише один main-клас `FlightApiApplication` та один тестовий клас із такою назвою, як у цьому архіві.
3. Відкрийте в IntelliJ IDEA, Gradle JVM = JDK 21. Windows PowerShell: `./gradlew.bat build`, потім `./gradlew.bat bootRun` (у Windows також можна ` .\gradlew.bat build`).
4. Імпортуйте `postman/Flight API.postman_collection.json` в Postman і виконайте запити по черзі. `flightId` встановлюється автоматично після POST.

## REST endpoints
- GET /flights — 200
- GET /flights/{id} — 200 або 404
- POST /flights — 201 або 400
- DELETE /flights/{id} — 204 або 404

Дані демонстраційні, зберігаються у статичному списку, відновлюються після перезапуску. Ціна умовно в EUR. API не має автентифікації та не призначений для production. Перевірка HTTP-методів у цьому середовищі не виконана: відсутній Gradle та мережевий доступ до залежностей. Wrapper надається Spring Initializr, а не цим архівом.
