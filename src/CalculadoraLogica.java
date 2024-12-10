public class CalculadoraLogica {
    private double operando1;
    private String operacion;

    public CalculadoraLogica() {
        operando1 = 0;
        operacion = "";
    }

    public void setOperando1(double operando) {
        this.operando1 = operando;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public double realizarOperacion(double operando2) {
        switch (operacion) {
            case "+":
                return operando1 + operando2;
            case "-":
                return operando1 - operando2;
            case "*":
                return operando1 * operando2;
            case "/":
                if (operando2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                return operando1 / operando2;
            default:
                return 0;
        }
    }
}
