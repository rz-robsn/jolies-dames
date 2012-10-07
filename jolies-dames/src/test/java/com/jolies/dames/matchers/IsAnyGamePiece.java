package com.jolies.dames.matchers;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import com.jolies.dames.utilities.model.GamePiece;

public class IsAnyGamePiece extends ArgumentMatcher<GamePiece>
{
    public IsAnyGamePiece()
    {
        super();
    }   
    
    @Override
    public boolean matches(Object argument)
    {        
        return argument.getClass().getName().equals(GamePiece.class.getName());
    }
    
    public static GamePiece isAnyGamePiece()
    {
        return Mockito.argThat(new IsAnyGamePiece());
    }
}
