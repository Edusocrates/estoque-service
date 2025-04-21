CREATE TABLE estoques (
    id UUID PRIMARY KEY,
    sku VARCHAR(255) NOT NULL UNIQUE,
    quantidade_disponivel INTEGER NOT NULL
);