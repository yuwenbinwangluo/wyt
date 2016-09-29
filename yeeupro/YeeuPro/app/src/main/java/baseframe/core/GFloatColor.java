package baseframe.core;

/**
 * 颜色对象
 */
public class GFloatColor {
    private float r;
    private float g;
    private float b;
    private float a;

    public GFloatColor(float r, float g, float b, float a)
    {
        this.r=r;
        this.g=g;
        this.b=b;
        this.a=a;
    }

    public float getA() {
        return a;
    }

    public void setA(short a) {
        this.a = a;
    }

    public float getR() {
        return r;
    }

    public void setR(short r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(short g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(short b) {
        this.b = b;
    }
}
