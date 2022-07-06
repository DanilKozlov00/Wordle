package wordle.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Type;
import wordle.controller.CharacterPosition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Schema
@Entity
@Table(name = "word_character")
public class WordCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "\"character\"", nullable = false, length = 1)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "step_id", nullable = false)
    @JsonIgnore
    @Hidden
    private Step step;

    @Enumerated(EnumType.STRING)
    @Type(type = "wordle.model.dto.EnumTypePostgreSql")
    @Column(name = "character_position")
    private CharacterPosition characterPosition;

    public CharacterPosition getCharacterPosition() {
        return characterPosition;
    }

    public void setCharacterPosition(CharacterPosition characterPosition) {
        this.characterPosition = characterPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

}