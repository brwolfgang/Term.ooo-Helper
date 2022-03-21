package me.olobo.java.termoohelper;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class TermoHelperTest {
    TermoHelper termoHelper;

    @Before
    public void setUp() throws Exception {
        termoHelper = new TermoHelper();
    }

    @Test
    public void deveGerarPatternCorretamente() {
        String consulta = "v.ado";

        String patternConsulta = termoHelper.gerarPatternConsulta(consulta);

        assertThat(patternConsulta, CoreMatchers.equalTo("v\\Dado"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveValidarInputVazio() {
        String[] args = new String[] {};
        
        TermoHelper.validarInput(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveValidarInputTamanhoIncorreto() {
        String[] args = new String[] {"rapadura"};

        TermoHelper.validarInput(args);
    }
}