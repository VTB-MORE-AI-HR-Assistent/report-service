# PDF API Testing Guide

## Новые PDF эндпоинты

Создан новый `PdfController` с двумя тестовыми эндпоинтами для генерации PDF:

### 1. Простой PDF (`/api/reports/pdf/simple`)

- **URL**: `GET /api/reports/pdf/simple`
- **Описание**: Генерирует простой PDF с базовым содержимым
- **Содержимое**: "Привет, мир!" и "Это красивый PDF отчет"
- **Файл**: `simple_report.pdf`

### 2. Детальный PDF (`/api/reports/pdf`)

- **URL**: `GET /api/reports/pdf`
- **Описание**: Генерирует детальный PDF с VTB AI HR брендингом
- **Содержимое**:
  - Заголовок с логотипом VTB AI HR
  - Таблица с данными кандидата
  - Заключение
- **Файл**: `vtb_ai_hr_report.pdf`

## Тестирование

### Автоматическое тестирование

Запустите тестовый скрипт:

```bash
./test-pdf-api.sh
```

### Ручное тестирование

1. **Запустите приложение**:

   ```bash
   ./gradlew bootRun
   ```

2. **Тестируйте простой PDF**:

   ```bash
   curl -X GET "http://localhost:8080/api/reports/pdf/simple" \
     -H "Accept: application/pdf" \
     -o "simple_test.pdf"
   ```

3. **Тестируйте детальный PDF**:

   ```bash
   curl -X GET "http://localhost:8080/api/reports/pdf" \
     -H "Accept: application/pdf" \
     -o "detailed_test.pdf"
   ```

4. **Тестируйте существующий эндпоинт**:
   ```bash
   curl -X GET "http://localhost:8080/api/v1/reports/pdf/test" \
     -H "Accept: application/pdf" \
     -o "existing_test.pdf"
   ```

### Проверка в браузере

Откройте в браузере:

- `http://localhost:8080/api/reports/pdf/simple`
- `http://localhost:8080/api/reports/pdf`

## Технические детали

### Используемые библиотеки

- **iText7 Core** (8.0.2) - для создания PDF
- **Spring Boot** - для REST API

### Особенности реализации

- Корректные HTTP заголовки для PDF
- Обработка ошибок
- Кэширование отключено
- Правильный Content-Type: `application/pdf`
- Content-Disposition для скачивания файла

### Структура PDF

1. **Заголовок** - центрированный, жирный шрифт
2. **Основной текст** - обычный шрифт
3. **Таблица** - с данными кандидата
4. **Заключение** - курсив, по центру

## Swagger документация

Обновлена Swagger документация (`swagger.json`) с новыми эндпоинтами:

- `/api/reports/pdf` - детальный PDF
- `/api/reports/pdf/simple` - простой PDF

## Устранение неполадок

### Если PDF не генерируется

1. Проверьте логи приложения
2. Убедитесь, что iText7 зависимости подключены
3. Проверьте доступность порта 8080

### Если PDF не открывается

1. Проверьте, что файл скачался полностью
2. Попробуйте открыть в разных PDF просмотрщиках
3. Проверьте размер файла (должен быть > 0 байт)

### Ошибки компиляции

1. Убедитесь, что используется Java 21
2. Выполните `./gradlew clean build`
3. Проверьте зависимости в `build.gradle.kts`
