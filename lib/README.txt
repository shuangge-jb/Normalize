normalize.jar是归一化的jar包
所有依赖的包在本目录中


代码示例：
e.g.
import domain.Normalize;
public class Main{
    public static void main(String[] args) {
        List<InitialProduct> products=...;
		new Normalize().normalize(products);
	}
}
