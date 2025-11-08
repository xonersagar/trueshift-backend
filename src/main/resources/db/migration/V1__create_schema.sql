-- V1__create_schema.sql
CREATE TABLE IF NOT EXISTS company (
  company_id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  industry VARCHAR(255),
  location VARCHAR(255),
  registration_no VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS employee (
  employee_id BIGSERIAL PRIMARY KEY,
  trust_code VARCHAR(100) UNIQUE,
  full_name VARCHAR(255) NOT NULL,
  dob DATE,
  email VARCHAR(255),
  aadhar_no VARCHAR(50),
  phone VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS employment_history (
  history_id BIGSERIAL PRIMARY KEY,
  employee_id BIGINT REFERENCES employee(employee_id) ON DELETE CASCADE,
  company_id BIGINT REFERENCES company(company_id) ON DELETE SET NULL,
  start_date DATE,
  end_date DATE,
  exit_reason TEXT,
  position VARCHAR(255),
  verified_by VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "user_account" (
  user_id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  company_id BIGINT REFERENCES company(company_id),
  role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS verification_request (
  request_id BIGSERIAL PRIMARY KEY,
  requested_by_company BIGINT REFERENCES company(company_id),
  employee_id BIGINT REFERENCES employee(employee_id),
  request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(50),
  employee_consent BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS redflag (
  flag_id BIGSERIAL PRIMARY KEY,
  employee_id BIGINT REFERENCES employee(employee_id) ON DELETE CASCADE,
  company_id BIGINT REFERENCES company(company_id),
  reason TEXT,
  flag_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  evidence_link TEXT
);
