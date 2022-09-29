ALTER TABLE question
    ADD COLUMN question_vector tsvector;

UPDATE
    question
SET
    question_vector = TO_TSVECTOR(COALESCE(question, ''));

CREATE INDEX question_question_vector_idx ON question USING GIN (question_vector);

