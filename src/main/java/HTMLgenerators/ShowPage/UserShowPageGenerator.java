package HTMLgenerators.ShowPage;

import Models.User;

import java.util.List;

public class UserShowPageGenerator extends ShowPageGenerator<User>
{
    public UserShowPageGenerator(List<User> list) {
        super(list);
    }

    @Override
    protected String title() {
        return super.title();
    }

    @Override
    protected String body() {
        return super.body();
    }

    @Override
    protected String table() {
        return super.table();
    }

    @Override
    protected String generateTableRow(User obj) throws IllegalAccessException {
        return super.generateTableRow(obj);
    }

    @Override
    protected String generateTableBody() {
        return super.generateTableBody();
    }
}
