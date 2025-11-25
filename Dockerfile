# Используем базовый образ Java OpenJDK
FROM maven:3.8-openjdk-17 AS build

# Копируем pom.xml и исходники
COPY pom.xml .
COPY src ./src

# Устанавливаем зависимости и собираем артефакт
RUN mvn clean package -DskipTests=true

# Переходим к финальному образу
FROM maven:3.8-openjdk-17

# Создаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранные файлы
COPY --from=build pom.xml ./
COPY --from=build src ./src

# Запуск приложения
CMD ["mvn", "test", "-Dtest=SmokePositiveTest"]
