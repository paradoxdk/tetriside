package componentes.visao;

import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class JTetrisButton extends JButton {
	public JTetrisButton(String titulo){
		super(titulo);
		registerKeyboardAction(getActionForKeyStroke(
				KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);

		registerKeyboardAction(
				getActionForKeyStroke(
						KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
						KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
						JComponent.WHEN_FOCUSED);
	}
}

