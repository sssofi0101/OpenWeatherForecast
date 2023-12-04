# OpenWeatherForecast
Приложение с прогнозом погоды от OpenWeather API <br> <br>
<a href="https://github.com/sssofi0101/OpenWeatherForecast/releases/download/1.0/1.0.apk">Скачать приложение </a>

 ## Реализованный функционал
 - Просмотр текущей погоды
 - Просмотр прогноза погоды на ближайшие 5 дней
 - Просмотр подробной погоды на выбранный день по нажатию на элемент списка
 - Нижняя панель навигации (BottomNavigationBar), где первая вкладка - раздел прогноза погоды, остальные вкладки - заглушки
 - Кеширование прогноза погоды. При открытии приложения без доступа в интернет данные загружаются из кеша (Room)
 ## Стек технологий
 - Kotlin <br>
 - MVVM, CleanArchitecture <br>
 - Retrofit2, OkHttp3, GsonConverter, Coroutines  <br>
 - LiveData, Room <br>
- Navigation component, Picasso, Secrets Gradle Plugin <br>
- Реализацию Dependency Injection с помощью Dagger2 можно увидеть в ветке <a href="https://github.com/sssofi0101/OpenWeatherForecast/tree/di">di</a>
<div style="display">
<img src="https://github.com/sssofi0101/OpenWeatherForecast/raw/pictures/screenshot1.jpg" width = "200">
<img src="https://github.com/sssofi0101/OpenWeatherForecast/raw/pictures/screenshot2.jpg" width = "200">
<img src="https://github.com/sssofi0101/OpenWeatherForecast/raw/pictures/screenshot3.jpg" width = "200">
</div>

 ## Важно
 При обычном клонировании репозитория проект будет собираться с ошибкой, поскольку ключ апи спрятан и не загружен на гитхаб. Для тестирования работы приложения можно скачать приложение по ссылке выше.
