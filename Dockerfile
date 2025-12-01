FROM ubuntu:latest
LABEL authors="amand"

ENTRYPOINT ["top", "-b"]