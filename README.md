# Crypto Telegram Bot

Backend service for tracking cryptocurrency prices and notifying users via Telegram.

---

## Overview

This project implements a Telegram bot that allows users to monitor cryptocurrency prices and receive notifications based on configured conditions.

The application demonstrates integration with external APIs, persistent storage, and bot interaction logic.

---

## Features

- Telegram bot interaction
- Cryptocurrency price tracking
- User subscriptions to target price
- Notifications on price changes
- Persistent storage with PostgreSQL
- Clean layered architecture

---

## Tech Stack

- Java 17  
- Spring Boot  
- Spring Data JPA  
- PostgreSQL (Docker)  
- Gradle  
- Telegram Bots API  

---

## Running the Application

### 1. Start database

```bash
docker-compose up -d

2. Configure application

Set your Telegram bot token in application.yml:

bot:
  token: YOUR_TOKEN

  3. Run application
./gradlew bootRun

or run CryptoBotApplication from IDE.

Purpose

This project demonstrates:

Integration with Telegram API
Working with external services
Backend architecture design
Persistent data handling



---

## 🇷🇺 Русская версия (оставь снизу)

```markdown
---

## 🇷🇺 Russian Version

### Как запустить

При открытии проекта в IntelliJ IDEA он должен автоматически распознаться.  
Появится run configuration `CryptoBotApplication`.  
Если нет — открой класс `CryptoBotApplication` и запусти `main`.

---

### База данных

Для работы используется PostgreSQL в Docker.

Запуск:

```bash
docker-compose up -d
