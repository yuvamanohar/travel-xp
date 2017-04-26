package models;

/**
 * Created by yuva on 17/4/17.
 */
public class SocialProfile extends JsonModel {
    public Long socialProfileId ;

    public String socialNetwork ;
    public String socialNetworkId ;
    public String firstName ;
    public String middleName ;
    public String lastName ;
    public String completeName ;
    public String profilePic ;


    public SocialProfile() {}

    public SocialProfile(Long socialProfileId, String socialNetwork, String socialNetworkId, String firstName, String middleName,
                            String lastName, String completeName, String profilePic) {
        this.socialProfileId = socialProfileId ;
        this.socialNetwork = socialNetwork ;
        this.socialNetworkId = socialNetworkId ;
        this.firstName = firstName ;
        this.middleName = middleName ;
        this.lastName = lastName ;
        this.completeName = completeName ;
        this.profilePic = profilePic ;
    }

}
