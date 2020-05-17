import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    private Complex p = new Complex(0, 0);
    private Complex q = new Complex(0, 0);
    public static final int MAX_ITERATIONS = 2000;
    private class Complex {
        private double real, imag;
        private Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }
        private double abs() {
            return real * real + imag * imag;
        }
        private Complex sum(Complex h) {
            return new Complex(this.real + h.real, this.imag + h.imag);
        }
        private Complex times(Complex h) {
            double real = this.real * h.real - this.imag * h.imag;
            double imag = this.real * h.imag + this.imag * h.real;
            return new Complex(real, imag);
        }
    }

    public void getInitialRange(Rectangle2D.Double rect) {
        rect.setRect(-2, -1.5, 3,3);
    }

    public int numIterations(double x, double y) {
        p.real = 0;
        p.imag = 0;
        q.real = x;
        q.imag = y;
        for (int NumOfIter = 0; NumOfIter < MAX_ITERATIONS; NumOfIter++) {
            p = p.times(p).sum(q);
            if (p.abs() > 4)
                return NumOfIter;
        }
        return -1;
    }
}