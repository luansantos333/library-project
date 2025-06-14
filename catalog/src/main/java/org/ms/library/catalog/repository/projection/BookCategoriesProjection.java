package org.ms.library.catalog.repository.projection;

import java.util.Set;

public interface BookCategoriesProjection {

    String getAuthor();
    String getTitle();
    Set<String> getCategoryName();
    Double getPrice();


}
