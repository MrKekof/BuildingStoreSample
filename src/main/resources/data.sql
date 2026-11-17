INSERT INTO catalog (name, description, catalog_image_url,
                     price, standard, diameter, material,
                     category, created_at, updated_at)
VALUES
    ('Труба стальная 20мм', 'Усиленная труба для систем отопления.',
     'https://example.com/images/pipe20.jpg',
     450.50, 'ГОСТ 3262-75', '20мм', 'Сталь', 'Трубы',
     NOW(), NOW()),
    ('Фитинг латунный 1/2', 'Соединительный элемент для труб.',
     'https://example.com/images/fitting12.jpg', 125.00, 'ISO 9001',
     '12мм', 'Латунь', 'Фитинги', NOW(), NOW()),
    ('Труба PPR 25мм', 'Полипропиленовая труба для холодного водоснабжения.',
     'https://example.com/images/ppr25.jpg', 85.00, 'DIN 8077',
     '25мм', 'Полипропилен', 'Трубы', NOW(), NOW()),
    ('Кран шаровый 3/4', 'Запорная арматура для водопроводных систем.',
     'https://example.com/images/valve34.jpg', 320.00, 'ГОСТ 21345-2005',
     '20мм', 'Латунь', 'Арматура', NOW(), NOW()),
    ('Насос циркуляционный', 'Мощный насос для систем индивидуального отопления.',
     'https://example.com/images/pump.jpg', 4500.00, 'CE',
     '1 дюйм', 'Чугун', 'Оборудование', NOW(), NOW()),
    ('Манометр радиальный', 'Прибор для измерения давления в системе.',
     'https://example.com/images/gauge.jpg', 680.00, 'ГОСТ 2405-88',
     '1/4', 'Медь/Сталь', 'Инструменты', NOW(), NOW()),
    ('Утеплитель для труб 32мм', 'Вспененный полиэтилен для теплоизоляции.',
     'https://example.com/images/iso32.jpg', 45.00, 'ТУ 22.21',
     '32мм', 'Полиэтилен', 'Изоляция', NOW(), NOW())
ON CONFLICT (name) DO NOTHING; -- Используем уникальное поле name для проверки конфликтов

INSERT INTO news (title, content, news_image_url, published, created_at, updated_at)
VALUES
    ('Мы открылись!', 'Наш магазин строительных материалов NavisDevs начал работу.', 'https://example.com/news/open.jpg', true, NOW(), NOW()),
    ('Скидки на фитинги', 'Весь март скидка 10% на все латунные изделия.', 'https://example.com/news/sale.jpg', true, NOW(), NOW()),
    ('Новое поступление PPR', 'На склад поступили трубы и фитинги из полипропилена высокого качества.', 'https://example.com/news/ppr-arrival.jpg', true, NOW(), NOW()),
    ('График работы на праздники', 'Внимание! 8 марта магазин работает до 15:00.', 'https://example.com/news/schedule.jpg', false, NOW(), NOW()) -- Черновик
ON CONFLICT (title) DO NOTHING; -- Проверка по уникальному заголовку

INSERT INTO pages (slug, title, content, created_at, updated_at)
VALUES
    ('about', 'О компании', 'NavisDevs — ваш надежный партнер в строительстве инженерных систем.',
     NOW(), NOW()),
    ('contacts', 'Контакты', 'Мы находимся по адресу: г. Строителей, д. 1. Телефон: +7 (999) 000-00-00.',
     NOW(), NOW()),
    ('delivery', 'Доставка и оплата', 'Осуществляем доставку по городу и области в течение 24 часов после заказа.',
     NOW(), NOW()),
    ('faq', 'Вопросы и ответы', 'Здесь вы найдете ответы на самые популярные вопросы о продукции.',
     NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

