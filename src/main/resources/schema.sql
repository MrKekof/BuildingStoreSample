CREATE TABLE IF NOT EXISTS catalog (
                                       id BIGSERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL UNIQUE ,
                                       description TEXT NOT NULL,
                                       catalog_image_url VARCHAR(255) NOT NULL,
                                       price NUMERIC(19, 2) NOT NULL,
                                       standard VARCHAR(255) NOT NULL,
                                       diameter VARCHAR(255) NOT NULL,
                                       material VARCHAR(255) NOT NULL,
                                       category VARCHAR(255) NOT NULL,
                                       created_at TIMESTAMP WITHOUT TIME ZONE,
                                       updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS images (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      content_type VARCHAR(255) NOT NULL,
                                      data OID NOT NULL,
                                      created_at TIMESTAMP WITHOUT TIME ZONE,
                                      updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS news (
                                    id BIGSERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL UNIQUE ,
                                    content TEXT NOT NULL,
                                    news_image_url VARCHAR(255) NOT NULL ,
                                    published BOOLEAN DEFAULT FALSE,
                                    created_at TIMESTAMP WITHOUT TIME ZONE,
                                    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS pages (
                                     id BIGSERIAL PRIMARY KEY,
                                     slug VARCHAR(255) NOT NULL UNIQUE,
                                     title VARCHAR(255) NOT NULL,
                                     content TEXT NOT NULL,
                                     created_at TIMESTAMP WITHOUT TIME ZONE,
                                     updated_at TIMESTAMP WITHOUT TIME ZONE
);