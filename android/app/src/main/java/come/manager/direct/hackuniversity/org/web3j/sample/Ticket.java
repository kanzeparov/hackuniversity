package come.manager.direct.hackuniversity.org.web3j.sample;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Ticket extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6040516109e33803806109e3833981016040528080518201919060200180519190602001805191906020018051820191906020018051820191906020018051820191906020018051820191906020018051600080547401000000000000000000000000000000000000000060a060020a60ff021990911617600160a060020a031916600160a060020a038a16179055600189905591909101905060038580516100bc929160200190610132565b5060028480516100d0929160200190610132565b5060048380516100e4929160200190610132565b5060068880516100f8929160200190610132565b50426007556008828051610110929160200190610132565b506009818051610124929160200190610132565b5050505050505050506101cd565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061017357805160ff19168380011785556101a0565b828001600101855582156101a0579182015b828111156101a0578251825591602001919060010190610185565b506101ac9291506101b0565b5090565b6101ca91905b808211156101ac57600081556001016101b6565b90565b610807806101dc6000396000f3006060604052600436106100c45763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630bc8649581146100c9578063146dee11146101535780631ca989cf146101c157806337b975a9146101d457806359ebeb90146102105780635e87942c146102235780636f5b83c0146102365780637947c107146102495780638052474d1461025c5780639dfde2011461026f578063af17afd514610294578063b0f9d0ca146102a7578063ff64bea8146102ba575b600080fd5b34156100d457600080fd5b6100dc6102e1565b60405160208082528190810183818151815260200191508051906020019080838360005b83811015610118578082015183820152602001610100565b50505050905090810190601f1680156101455780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015e57600080fd5b6101bf6004803573ffffffffffffffffffffffffffffffffffffffff169060446024803590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965061038a95505050505050565b005b34156101cc57600080fd5b6100dc61042b565b34156101df57600080fd5b6101e761049e565b60405173ffffffffffffffffffffffffffffffffffffffff909116815260200160405180910390f35b341561021b57600080fd5b6101bf6104ba565b341561022e57600080fd5b6100dc610532565b341561024157600080fd5b6100dc6105a5565b341561025457600080fd5b6100dc610618565b341561026757600080fd5b6100dc61068b565b341561027a57600080fd5b6102826106fe565b60405190815260200160405180910390f35b341561029f57600080fd5b610282610704565b34156102b257600080fd5b61028261070a565b34156102c557600080fd5b6102cd610710565b604051901515815260200160405180910390f35b6102e9610731565b60048054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561037f5780601f106103545761010080835404028352916020019161037f565b820191906000526020600020905b81548152906001019060200180831161036257829003601f168201915b505050505090505b90565b60005474010000000000000000000000000000000000000000900460ff1615156001146103b657600080fd5b6000543373ffffffffffffffffffffffffffffffffffffffff9081169116146103de57600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff84161790556003818051610426929160200190610743565b505050565b610433610731565b60088054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561037f5780601f106103545761010080835404028352916020019161037f565b60005473ffffffffffffffffffffffffffffffffffffffff1690565b60005474010000000000000000000000000000000000000000900460ff1615156001146104e657600080fd5b6000543373ffffffffffffffffffffffffffffffffffffffff90811691161461050e57600080fd5b6000805474ff00000000000000000000000000000000000000001916905542600555565b61053a610731565b60028054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561037f5780601f106103545761010080835404028352916020019161037f565b6105ad610731565b60038054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561037f5780601f106103545761010080835404028352916020019161037f565b610620610731565b60098054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561037f5780601f106103545761010080835404028352916020019161037f565b610693610731565b60068054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561037f5780601f106103545761010080835404028352916020019161037f565b60015490565b60055490565b60075490565b60005474010000000000000000000000000000000000000000900460ff1690565b60206040519081016040526000815290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061078457805160ff19168380011785556107b1565b828001600101855582156107b1579182015b828111156107b1578251825591602001919060010190610796565b506107bd9291506107c1565b5090565b61038791905b808211156107bd57600081556001016107c75600a165627a7a72305820aef920068b107f6f7b9e1f3290ffe24b41cd648c4e5da6ad7dc984f8e14cde750029";

    public static final String FUNC_TICKET_ID = "Ticket_ID";

    public static final String FUNC_DELEGATE = "Delegate";

    public static final String FUNC_DATE = "Date";

    public static final String FUNC_WHO_OWNER = "Who_owner";

    public static final String FUNC_OPEN = "Open";

    public static final String FUNC_PLACE = "Place";

    public static final String FUNC_PERSON_INFO = "Person_info";

    public static final String FUNC_GET_ALL = "Get_all";

    public static final String FUNC_NAME = "Name";

    public static final String FUNC_PRICE = "Price";

    public static final String FUNC_WHEN_ACTIVE = "When_active";

    public static final String FUNC_WHEN_OPEN = "When_open";

    public static final String FUNC_IS_ACTIVE = "Is_active";

    protected Ticket(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ticket(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> Ticket_ID() {
        final Function function = new Function(FUNC_TICKET_ID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> Delegate(String _user, String _person_info) {
        final Function function = new Function(
                FUNC_DELEGATE, 
                Arrays.<Type>asList(new Address(_user),
                new Utf8String(_person_info)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> Date() {
        final Function function = new Function(FUNC_DATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> Who_owner() {
        final Function function = new Function(FUNC_WHO_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> Open() {
        final Function function = new Function(
                FUNC_OPEN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> Place() {
        final Function function = new Function(FUNC_PLACE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> Person_info() {
        final Function function = new Function(FUNC_PERSON_INFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> Get_all() {
        final Function function = new Function(FUNC_GET_ALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> Name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> Price() {
        final Function function = new Function(FUNC_PRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> When_active() {
        final Function function = new Function(FUNC_WHEN_ACTIVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> When_open() {
        final Function function = new Function(FUNC_WHEN_OPEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> Is_active() {
        final Function function = new Function(FUNC_IS_ACTIVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<Ticket> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _name, BigInteger _price, String _user, String _person_info, String _place, String _ticket_id, String _date, String _all) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(_name),
                new Uint256(_price),
                new Address(_user),
                new Utf8String(_person_info),
                new Utf8String(_place),
                new Utf8String(_ticket_id),
                new Utf8String(_date),
                new Utf8String(_all)));
        return deployRemoteCall(Ticket.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Ticket> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _name, BigInteger _price, String _user, String _person_info, String _place, String _ticket_id, String _date, String _all) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(_name),
                new Uint256(_price),
                new Address(_user),
                new Utf8String(_person_info),
                new Utf8String(_place),
                new Utf8String(_ticket_id),
                new Utf8String(_date),
                new Utf8String(_all)));
        return deployRemoteCall(Ticket.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Ticket load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ticket(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Ticket load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ticket(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
