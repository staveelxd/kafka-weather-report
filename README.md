# Kafka Weather Report
## Описание 
Демонстрационное приложение, состоящее из двух компонентов: продюсера и консюмера сообщений о погоде. Продюсер отправляет сообщения в Kafka-топик, а консюмер их получает и обрабатывает. Проект разработан в рамках первого воркшопа.
## Установка и запуск с помощью Docker

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/staveelxd/kafka-weather-report.git
cd kafka-weather-report
```
### 2. Соберите JAR файл приложения
```bash
mvn clean package
```
### 3. Создайте Docker образ
```bash
docker build -t weather-app .
```
### 4. Запустите приложение в контейнере
```bash
docker run -p 8080:8080 weather-app
```
