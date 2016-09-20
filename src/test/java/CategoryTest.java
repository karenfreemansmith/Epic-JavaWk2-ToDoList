import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class CategoryTest {

  @Before
  public void setup() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
  }

  @Test
  public void category_instantiatesCorrectly_true() {
    Category testCategory = new Category("Home");
    assertEquals(true, testCategory instanceof Category);
  }

  @Test
  public void getName_categoryInstantiatesWithName_Home() {
    Category testCategory = new Category("Home");
    assertEquals("Home", testCategory.getName());
  }

  @Test
  public void all_returnsAllInstancesOfCategory_true() {
    Category firstCategory = new Category("Home");
    firstCategory.save();
    Category secondCategory = new Category("Work");
    secondCategory.save();
    assertEquals(true, Category.all().get(0).equals(firstCategory));
    assertEquals(true, Category.all().get(1).equals(secondCategory));
  }

  @Test
  public void clear_emptiesAllCategoriesFromList_0() {
    Category testCategory = new Category("Home");
    Category.clear();
    assertEquals(Category.all().size(), 0);
  }

  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
    Category testCategory = new Category("Home");
    testCategory.save();
    assertEquals(testCategory.getId()>0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreSame() {
    Category firstCategory = new Category("Household chores");
    Category secondCategory = new Category("Household chores");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    assertTrue(Category.all().get(0).equals(myCategory));
  }

  @Test
  public void save_assignsIdToObject() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Category savedCategory = Category.all().get(0);
    assertEquals(myCategory.getId(), savedCategory.getId());
  }

  @Test
  public void find_returnsCategoryWithSameId_secondCategory() {
    Category firstCategory = new Category("Home");
    firstCategory.save();
    Category secondCategory = new Category("Work");
    secondCategory.save();
    assertEquals(Category.find(secondCategory.getId()), secondCategory);
  }

  @Test
  public void getTasks_initiallyReturnsEmptyList_ArrayLis() {
    Category.clear();
    Category testCategory = new Category("Home");
    assertEquals(0, testCategory.getTasks().size());
  }

  @Test
  public void addTask_addsTaskToList_true() {
    Category testCategory = new Category("Home");
    Task testTask = new Task("Mow the lawn");
    testCategory.addTask(testTask);
    assertTrue(testCategory.getTasks().contains(testTask));
  }

  @After
  public void tearDown() {
    try(Connection con=DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;"
      String deleteCategoriesQuery = "DELETE FROM categories *;"
      con.createQuery(deleteTasksQuery).execteUpdate();
      con.createQuery(deleteCategoriesQuery).execteUpdate();
    }
  }
}
