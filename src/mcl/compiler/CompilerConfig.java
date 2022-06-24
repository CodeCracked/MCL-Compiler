package mcl.compiler;

public class CompilerConfig
{
    public int floatDecimalPlaces = 3;
    public boolean simplifyExpressions = true;
    public String projectName = "mcl";

    public String floatScaleDown() { return String.format("0.%s1", "0".repeat(floatDecimalPlaces - 1)); }
    public String floatScaleUp() { return String.format("1%s", "0".repeat(floatDecimalPlaces)); }

    public String variablesStorage() { return projectName + ":variables"; }
    public String expressionsObjective() { return projectName + ".expressions"; }
    public String constantsObjective() { return projectName + ".constants"; }
}
