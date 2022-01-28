package data.source;

import data.dao.ILibraryRepository;
import data.repository.Entity;

public interface IRepositiry {

    void showTable(Type type);

    public void getById (Type type,  int Id);

    public void getByText (Type type,  String text);

}
