CREATE TABLE robots_txt (
    id serial NOT NULL,
    "key" varchar(2000) NOT NULL,
    "value" varchar(2000) NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    CONSTRAINT robots_txt_id_pkey PRIMARY KEY (id)
);

CREATE TABLE sitemap_index (
    id serial NOT NULL,
    sitemap_url_identifier varchar(2000) NOT NULL,
    CONSTRAINT sitemap_index_id_pkey PRIMARY KEY (id)
);

CREATE TABLE sitemap (
    id serial NOT NULL,
    sitemap_id int NOT NULL,
    "url" varchar(2000) NOT NULL,
    last_mod timestamp without time zone DEFAULT NOW(),
    CONSTRAINT sitemap_id_pkey PRIMARY KEY (id),
    CONSTRAINT sitemap_sitemap_id_fkey FOREIGN KEY (sitemap_id) REFERENCES sitemap_index (id)
);

CREATE TABLE "role" (
    id serial NOT NULL,
    "role" varchar(255) NOT NULL,
    CONSTRAINT role_id_pkey PRIMARY KEY (id)
);

CREATE TABLE gender (
    id serial NOT NULL,
    gender varchar(2) NOT NULL,
    CONSTRAINT gender_id_pkey PRIMARY KEY (id)
);

CREATE TABLE "subject" (
    id serial NOT NULL,
    "subject" varchar(255) NOT NULL,
    CONSTRAINT subject_id_pkey PRIMARY KEY (id)
);

CREATE TABLE tag (
    id serial NOT NULL,
    tag varchar(255) NOT NULL,
    CONSTRAINT tag_id_pkey PRIMARY KEY (id)
);

CREATE TABLE grade (
    id serial NOT NULL,
    grade varchar(255) NOT NULL,
    CONSTRAINT grade_id_pkey PRIMARY KEY (id)
);

CREATE TABLE topic (
    id serial NOT NULL,
    topic varchar(255) NOT NULL,
    CONSTRAINT topic_id_pkey PRIMARY KEY (id)
);

CREATE TABLE concept (
    id serial NOT NULL,
    concept varchar(255) NOT NULL,
    CONSTRAINT concept_id_pkey PRIMARY KEY (id)
);

CREATE TABLE users (
    id bigserial NOT NULL,
    user_no bigint NOT NULL,
    first_name varchar(30) NOT NULL,
    last_name varchar(40),
    email varchar(40) NOT NULL,
    gender int NOT NULL,
    mobile_no varchar(20),
    "password" varchar(455) NOT NULL,
    "role" int NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    deleted boolean DEFAULT FALSE,
    CONSTRAINT users_id_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_ukey UNIQUE (email),
    CONSTRAINT users_user_no_ukey UNIQUE (user_no),
    CONSTRAINT users_role_fkey FOREIGN KEY ("role") REFERENCES "role" (id),
    CONSTRAINT users_gender_fkey FOREIGN KEY (gender) REFERENCES gender (id)
);

CREATE TABLE question (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    question text NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    no_of_answers int DEFAULT 0,
    votes int DEFAULT 0,
    CONSTRAINT question_id_pkey PRIMARY KEY (id),
    CONSTRAINT question_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE question_update (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    question_id bigint NOT NULL,
    updated_question text NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    votes int DEFAULT 0,
    CONSTRAINT question_update_id_pkey PRIMARY KEY (id),
    CONSTRAINT question_update_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT question_update_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_subject (
    subject_id int NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT question_subject_subject_id_fkey FOREIGN KEY (subject_id) REFERENCES subject (id),
    CONSTRAINT question_subject_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_tag (
    tag_id int NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT question_tag_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES tag (id),
    CONSTRAINT question_tag_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_grade (
    grade_id int NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT question_grade_subject_id_fkey FOREIGN KEY (grade_id) REFERENCES grade (id),
    CONSTRAINT question_grade_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_topic (
    topic_id int NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT question_topic_subject_id_fkey FOREIGN KEY (topic_id) REFERENCES topic (id),
    CONSTRAINT question_topic_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_concept (
    concept_id int NOT NULL,
    question_id bigint NOT NULL,
    CONSTRAINT question_concept_subject_id_fkey FOREIGN KEY (concept_id) REFERENCES concept (id),
    CONSTRAINT question_concept_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE answer (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    answer text NOT NULL,
    question_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    votes int DEFAULT 0,
    CONSTRAINT answer_id_pkey PRIMARY KEY (id),
    CONSTRAINT answer_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT answer_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE answer_update (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    answer_id bigint NOT NULL,
    updated_answer text NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    votes int DEFAULT 0,
    CONSTRAINT answer_update_id_pkey PRIMARY KEY (id),
    CONSTRAINT answer_update_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT answer_update_answer_id_fkey FOREIGN KEY (answer_id) REFERENCES answer (id)
);

CREATE TABLE "address" (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    house_no text NOT NULL,
    city varchar(255) NOT NULL,
    state varchar(255) NOT NULL,
    country varchar(255) NOT NULL,
    pin_code varchar(255) NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    CONSTRAINT address_id_pkey PRIMARY KEY (id),
    CONSTRAINT address_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_detial (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    pan varchar(20),
    dob date NOT NULL,
    CONSTRAINT user_detial_id_pkey PRIMARY KEY (id),
    CONSTRAINT user_detial_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE consent_type (
    id serial NOT NULL,
    consent_type varchar(255) NOT NULL,
    CONSTRAINT consent_type_id_pkey PRIMARY KEY (id)
);

CREATE TABLE user_consent (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    consent_type_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    CONSTRAINT user_consent_id_pkey PRIMARY KEY (id),
    CONSTRAINT user_consent_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_consent_consent_type_id_fkey FOREIGN KEY (consent_type_id) REFERENCES consent_type (id)
);

CREATE TABLE asset_type (
    id serial NOT NULL,
    asset_type varchar(255) NOT NULL,
    CONSTRAINT asset_type_id_pkey PRIMARY KEY (id)
);

CREATE TABLE user_asset (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    asset_type_id int NOT NULL,
    url varchar(2000) NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    CONSTRAINT user_asset_id_pkey PRIMARY KEY (id),
    CONSTRAINT user_asset_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_asset_asset_type_id_fkey FOREIGN KEY (asset_type_id) REFERENCES asset_type (id)
);

CREATE TABLE complaint_category (
    id serial NOT NULL,
    category_type varchar(255) NOT NULL,
    CONSTRAINT complaint_category_id_pkey PRIMARY KEY (id)
);

CREATE TABLE complaint_sub_category (
    id serial NOT NULL,
    sub_category_type varchar(255) NOT NULL,
    CONSTRAINT complaint_sub_category_id_pkey PRIMARY KEY (id)
);

CREATE TABLE user_complaint (
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    complaint_category_id int NOT NULL,
    complaint_sub_category_id int NOT NULL,
    complaint_description text NOT NULL,
    created_at timestamp without time zone DEFAULT NOW(),
    updated_at timestamp without time zone DEFAULT NOW(),
    CONSTRAINT user_complaint_id_pkey PRIMARY KEY (id),
    CONSTRAINT user_complaint_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_complaint_complaint_category_id_fkey FOREIGN KEY (complaint_category_id) REFERENCES complaint_category (id),
    CONSTRAINT user_complaint_complaint_sub_category_id_fkey FOREIGN KEY (complaint_sub_category_id) REFERENCES complaint_sub_category (id)
);

