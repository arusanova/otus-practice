package ru.otus.swimming.exception

import ru.otus.swimming.models.ClSrvCommand

class UnknownClSrvCommand(command: ClSrvCommand) : Throwable("Wrong command $command at mapping toTransport stage")
