package br.net.mirante.singular.flow.core;

import br.net.mirante.singular.commons.base.SingularException;

/**
 * The base class of all runtime exceptions for Singular-Flow.
 *
 * @see SingularException
 */
public class SingularFlowException extends SingularException{

    /**
     * Constructs a new <code>SingularFlowException</code> without specified
     * detail message.
     */
    public SingularFlowException() {
        super();
    }

    /**
     * Constructs a new <code>SingularFlowException</code> with specified
     * detail message.
     *
     * @param msg the error message
     */
    public SingularFlowException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new <code>SingularFlowException</code> with specified
     * cause <code>Throwable</code>.
     *
     * @param cause the exception or error that caused this exception to be
     * thrown
     */
    public SingularFlowException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new <code>SingularFlowException</code> with specified
     * detail message and cause <code>Throwable</code>.
     *
     * @param msg    the error message
     * @param cause  the exception or error that caused this exception to be
     * thrown
     */
    public SingularFlowException(String msg, Throwable cause) {
        super(msg, cause);
    }
}