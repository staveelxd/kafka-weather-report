# Kafka Weather Report
## Описание 
Демонстрационное приложение, состоящее из двух компонентов: продюсера и консюмера сообщений о погоде. Продюсер отправляет сообщения в Kafka-топик, а консюмер их получает и обрабатывает. Проект разработан в рамках первого воркшопа.
## Установка и запуск с помощью Docker

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/staveelxd/kafka-weather-report.git
cd kafka-weather-report
```
### 2. Создайте Docker образы
```bash
docker-compose build 
```
### 3. Запустите контейнер
```bash
docker-compose up
```
