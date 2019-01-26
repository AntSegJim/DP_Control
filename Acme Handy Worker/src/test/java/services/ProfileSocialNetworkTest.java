
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ProfileSocialNetwork;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfileSocialNetworkTest extends AbstractTest {

	@Autowired
	private ProfileSocialNetworkService	profileSocialNetworkService;


	@Test
	public void testCreateProfile() {
		final ProfileSocialNetwork profile;

		profile = this.profileSocialNetworkService.create();
		profile.setNickName("Cristian");
		profile.setNameSocialNetwork("Twitter");
		profile.setLinkProfile("https://www.instagram.com/p/BpK6fX7HDmq/");
		Assert.isTrue(profile.getNickName().equals("Cristian") && profile.getNameSocialNetwork().equals("Twitter") && profile.getLinkProfile().equals("https://www.instagram.com/p/BpK6fX7HDmq/"));

	}

	@Test
	public void testSaveProfile() {
		ProfileSocialNetwork profile, saved;
		Collection<ProfileSocialNetwork> profiles;

		profile = this.profileSocialNetworkService.create();
		profile.setNickName("Pedro");
		profile.setNameSocialNetwork("Twitter");
		profile.setLinkProfile("https://www.instagram.com/p/BpK6fX7HDmq/");

		saved = this.profileSocialNetworkService.save(profile);
		profiles = this.profileSocialNetworkService.findAll();
		Assert.isTrue(profiles.contains(saved));

	}

	@Test
	public void testDeleteProfile() {
		ProfileSocialNetwork profile, saved;
		Collection<ProfileSocialNetwork> profiles;

		profile = this.profileSocialNetworkService.create();
		profile.setNickName("Pedro");
		profile.setNameSocialNetwork("Twitter");
		profile.setLinkProfile("https://www.instagram.com/p/BpK6fX7HDmq/");

		saved = this.profileSocialNetworkService.save(profile);
		this.profileSocialNetworkService.delete(saved);
		profiles = this.profileSocialNetworkService.findAll();
		Assert.isTrue(!profiles.contains(saved));
	}

}
