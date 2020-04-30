package HTMLgenerators.ShowPage;

import HTMLgenerators.ShowPage.ShowPageGenerator;
import Models.Friends;

import java.util.List;

public class FriendsShowPageGenerator extends ShowPageGenerator<Friends>
{

    public FriendsShowPageGenerator(List<Friends> list) {
        super(list);
    }
}
