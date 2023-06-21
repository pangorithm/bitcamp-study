package bitcamp.util;

public class Menu {
  private String title;
  private ArrayList listeners = new ArrayList();

  public Menu(String title) {
    this.title = title;
  }

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  public void removeActionListener(ActionListener listener) {
    listeners.remove(listener);
  }

  public String getTitle() {
    return title;
  }

  public void execute(BreadcrumbPrompt prompt) {
    for (int i = 0; i < listeners.size(); i++) {
      ActionListener listener = (ActionListener) listeners.get(i);
      listener.service(prompt);
    }
  }
}