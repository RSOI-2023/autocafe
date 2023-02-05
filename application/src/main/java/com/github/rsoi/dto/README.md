# DTO

Это пакет для DTO.

DTO — data transfer object, объект, в который оборачиваются пользовательские запросы и ответы на них.

DTO нужны для того, чтобы отделить структуру самих запросов от доменных сущностей и внутренних POJO.
Благодаря дополнительной обёртке их удобно передавать в сервисы и добавления полей во внутренние классы не влияют на получаемый пользователем ответ.