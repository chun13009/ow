package net.vtst.ow.eclipse.js.closure.preferences;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Control;

import net.vtst.eclipse.easy.ui.properties.editors.ICompositeEditor;
import net.vtst.eclipse.easy.ui.properties.pages.EasyPreferencePage;
import net.vtst.ow.eclipse.js.closure.OwJsClosureMessages;
import net.vtst.ow.eclipse.js.closure.OwJsClosurePlugin;
import net.vtst.ow.eclipse.js.closure.builder.ClosureBuilder;

public class ClosurePreferencePage extends EasyPreferencePage {

  private OwJsClosureMessages messages = OwJsClosurePlugin.getDefault().getMessages();
  private ClosurePreferenceEditor editor;

  @Override
  protected ICompositeEditor createEditor() {
    editor = new ClosurePreferenceEditor(this);
    return editor;
  }
  
  public IPreferenceStore getPreferenceStore() {
    return OwJsClosurePlugin.getDefault().getPreferenceStore();
  }

  @Override
  public String getMessage(String key) {
    return messages.getStringOrNull("ClosurePreferencePage_" + key);
  }
  
  public boolean performOk() {
    // This is not implemented as a change listener, because a change listener would be modified
    // of property changes individually.
    try {
      boolean hasChanged = editor.hasChanged(getStore());
      boolean ok = super.performOk();
      if (hasChanged && ok) {
        OwJsClosurePlugin.getDefault().getJSLibraryManager().clear();
        OwJsClosurePlugin.getDefault().getProjectOrderManager().clear();
        ClosureBuilder.buildAll();
      }
      return ok;
    } catch (CoreException e) {
      return false;
    }
  }

}
