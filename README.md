# CommerClient
desktop-клиент на JavaFX для проекта ЦБС-контракты&CRM [ЦБС-контракты&CRM ](https://github.com/SergePauli/CRM_Java)

## Simple JavaFX desktop client implementing the functionality of working with local files, not included in the web version due to security restrictions
В JavaFX может использоваться язык разметки FXML, что позволило быстро создать интерфейс приложения,
подобный HTML, но для локальной JVM пользователя, куда была вынесена часть функционала касающегося работы с общими файлами в локальной сети, невозможной в браузере из-за соображений безопасности. 

## Compatibility
for  Java 8-10

## Functional
 * авторизация пользователя в системе и получение карточки контракта, открытого в браузере 
 * заполнение ссылок на локальные файлы в карточке контракта (проект договора, протоколы, скан договора) через файл-мэнеджер ОС 
 * открытие файлов контракта в ассоциированых с их типами приложениях на клиентской ОС

 ![скрин](https://github.com/SergePauli/CommerClient/scrn.jpeg "Вид карточки CommerClient")

