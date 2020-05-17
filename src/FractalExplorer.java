import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
    private int m_DisplaySize;
    private JImageDisplay m_Display;
    private JButton m_Button;
    private JFrame m_Frame;
    private FractalGenerator m_Generator;
    private Rectangle2D.Double  m_Range;


    private class actionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            m_Generator.getInitialRange(m_Range);
            drawFractal();
        }
    }

    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            double xCoord = m_Generator.getCoord(m_Range.x, m_Range.x + m_Range.width, m_DisplaySize,x);
            double yCoord = m_Generator.getCoord(m_Range.y, m_Range.y + m_Range.height, m_DisplaySize,y);
            m_Generator.recenterAndZoomRange(m_Range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    public FractalExplorer(int ScreenSize) {
        m_DisplaySize = ScreenSize;

        m_Range = new Rectangle2D.Double();
        m_Generator = new Mandelbrot();
        m_Generator.getInitialRange(m_Range);
    }

    public void createAndShowGUI() {
        m_Frame  = new JFrame();
        m_Display = new JImageDisplay(m_DisplaySize, m_DisplaySize);
        m_Button = new JButton("Обновить");

        m_Frame.getContentPane().add(m_Display, BorderLayout.CENTER);
        m_Frame.getContentPane().add(m_Button, BorderLayout.SOUTH);
        m_Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        m_Display.addMouseListener(new MouseListener());
        m_Button.addActionListener(new actionListener());


        m_Frame.pack();
        m_Frame.setVisible(true);
        m_Frame.setResizable(true);
    }

    private void drawFractal() {
        for (int x = 0; x < m_DisplaySize; x++)
        {
            for (int y = 0; y < m_DisplaySize; y++)
            {
                // coord convertion
                double xCoord = FractalGenerator.getCoord
                        (m_Range.x, m_Range.x + m_Range.width, m_DisplaySize, x);
                double yCoord = FractalGenerator.getCoord
                        (m_Range.y, m_Range.y + m_Range.height, m_DisplaySize, y);
                //color calculating
                int IterNum = m_Generator.numIterations(xCoord, yCoord);
                if (IterNum == -1) m_Display.drawPixel(x, y, 0);
                else {
                    float hue = 0.7f + (float) IterNum / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    m_Display.drawPixel(x, y, rgbColor);
                }
            }
        }
        m_Display.repaint();
    }

    public static void main(String args[]) {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
}