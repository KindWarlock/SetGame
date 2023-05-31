# SetGame
Мобильное приложение для игры в Set, использующее сервер  [https://github.com/vankad24/GameSetServer](https://github.com/vankad24/GameSetServer).

# gradle

### Зависимости
- `implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1'`
- `implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"`
- `implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"`
- `implementation 'com.squareup.retrofit2:retrofit:2.9.0'`
- `implementation 'com.squareup.retrofit2:converter-gson:2.9.0'`

### binding
```
buildFeatures {
        viewBinding = true
}
```

# Custom views
Только один, для отображения карт: `CardView`. Является наследником `LinearLayout`, в котором хранятся `ImageView`s с нужными фигурами. При создании в xml поддерживает кастомные атрибуты:
- `count` - количество: 1-3
- `shape` - форма: wave, ellipse, romb
- `color` - цвет: red, blue, green
- `fill` - вариант заливки: none, filled, pattern.
Все комбинации фигур и вариантов заливки хрянятся в drawable.

# Layers
Мы тут пытаемся (но только когда нам это удобно) в архитектуру, hence 

## Data layer
Состоит из двух репозиториев и работы с ретрофитом. 

### API
- `NetHandler` и `SetAPI` - работа с API. Nuff said.
- `SetRequest` и `Login` - классы реквестов. 
- `SetResponse`, `Card` и `Field` - классы респонсов. Заявленный в API формат, в котором каждый ответ содержит поля `success` и `error` - ложь и провокация, поэтому существует класс `Field`. `Card` вынесен отдельно, т.к. является важной сущностью.

### Репозитории
Umm, actually обращения к интерфейсу api следовует вынести в DataSource классы, но ~~я забыла~~ у меня не хватило времени.
- `GameRepository` - репозиторий игрового поля. Текущие карты, запросы на изменение и очки - все к нему.
- `RoomRepository` - репозиторий комнат. В обязанности входят получение списка, вход и покидание комнаты.

## UI layer

### Activities
- `MainActivity` - регистрация и вход.
- `RoomsActivity` - список комнат (активных и неактивных).
- `GameActivity` - сама игра.

### Everything else
- `GameViewModel` и `GameUIState` - хранят состояние UI во время игры, передают его через kotlin flows. 
- `RoomsViewModel` и `RoomsUIState` - хранят состояние UI во время поиска комнат, передают его через kotlin flows. 
- `RoomsAdapter` - адаптер для RecyclerView с отображением комнат.
- `CardsInteraction` и `RoomsInteraction` - для взаимодействия с соответствующими элементами.

