package cn.edu.hit.ir.JNN.Nodes;

import cn.edu.hit.ir.JNN.ComputationGraph;
import cn.edu.hit.ir.JNN.Dim;
import cn.edu.hit.ir.JNN.Expression;
import cn.edu.hit.ir.JNN.Model;
import cn.edu.hit.ir.JNN.Trainers.SimpleSGDTrainer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by dancingsoul on 2016/5/13.
 */
public class MatrixMultiplyTest {
  @Test
  public void testGradient() throws Exception {
    Model m = new Model();
    SimpleSGDTrainer sgd = new SimpleSGDTrainer(m);
    ComputationGraph cg = new ComputationGraph();

    Vector<Double> xV = new Vector<Double>(Arrays.asList(1.0, 2.0));
    Vector<Double> yV = new Vector<Double>(Arrays.asList(1.0, 2.0));
    Vector<Double> zV = new Vector<Double>(Arrays.asList(2.0, 3.0));
    Expression W = Expression.Creator.parameter(cg, m.addParameters(Dim.create(2, 1)));

    Expression x = Expression.Creator.input(cg, Dim.create(2, 1), xV);
    Expression y = Expression.Creator.input(cg, Dim.create(2, 1), yV);
    Expression z = Expression.Creator.input(cg, Dim.create(1, 2), zV);

    Expression t = Expression.Creator.multiply(x, z);
    Expression s = Expression.Creator.multiply(t, W);
    Expression loss = Expression.Creator.squaredDistance(s, y);

    cg.gradientCheck();
    cg.forward();
    cg.backward();
    Assert.assertEquals(true, m.gradientCheck());
  }
}
