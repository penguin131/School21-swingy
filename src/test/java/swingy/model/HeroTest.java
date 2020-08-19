package swingy.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnit4.class)
public class HeroTest {

	@Test
	public void artifactsTest() {
		Hero character = new Hero(null, 0, 0, 10, new HashMap<>(), 1);
		Hero.HeroBuilder builder = new Hero.HeroBuilder(character);
		assertEquals(character.getArtifacts().size(), 0);
		builder.takeArtifact(new Artifact(ArtifactType.ARMOR, 5));
		assertEquals(character.getDefence(), 5);
		assertEquals(character.getAttack(), 0);
		assertEquals(character.getHitPoint(), 10);
		builder.takeArtifact(new Artifact(ArtifactType.ARMOR, 3));
		assertEquals(character.getDefence(), 3);
		assertEquals(character.getAttack(), 0);
		assertEquals(character.getHitPoint(), 10);
		builder.takeArtifact(new Artifact(ArtifactType.WEAPON, 3));
		assertEquals(character.getDefence(), 3);
		assertEquals(character.getAttack(), 3);
		assertEquals(character.getHitPoint(), 10);
		builder.takeArtifact(new Artifact(ArtifactType.HELM, 333));
		assertEquals(character.getDefence(), 3);
		assertEquals(character.getAttack(), 3);
		assertEquals(character.getHitPoint(), 343);
		builder.takeArtifact(new Artifact(ArtifactType.HELM, 1));
		assertEquals(character.getDefence(), 3);
		assertEquals(character.getAttack(), 3);
		assertEquals(character.getHitPoint(), 11);
	}

	@Test
	public void validationTest() {
		Hero character = new Hero(null, 0, 0, 10, new HashMap<>(), 1);
		Hero.HeroBuilder builder = new Hero.HeroBuilder(character);
		//validation
		builder.setName(null);
		Set<ConstraintViolation<Hero>> violations = builder.validate();
		assertEquals(2, violations.size());//hero class and name null
		builder.setName("22");
		violations = builder.validate();
		assertEquals(2, violations.size());//small name
		builder.setName("2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
		violations = builder.validate();
		assertEquals(2, violations.size());//huge name
		builder.setName("22222222222");
		violations = builder.validate();
		assertEquals(1, violations.size());//normal name
		builder.setHeroClass(HeroClass.MAN);
		violations = builder.validate();
		assertEquals(0, violations.size());//set hero class name
		builder.setLevel(0);
		violations = builder.validate();
		assertEquals(1, violations.size());//level 0
	}
}
