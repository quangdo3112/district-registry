pragma solidity ^0.4.18;

import "./Registry.sol";
import "./proxy/Forwarder1.sol";
import "@aragon/apps-shared-minime/contracts/MiniMeToken.sol";

/**
 * @title Base Factory contract for creating RegistryEntry contracts
 *
 * @dev This contract is meant to be extended by other factory contracts
 */

contract RegistryEntryFactory is ApproveAndCallFallBack {
  Registry public registry;
  MiniMeToken public registryToken;
  bytes32 public constant depositKey = keccak256("deposit");

  /**
   * @dev Modifier that disables function if registry is in emergency state
   */
  modifier notEmergency() {
    require(!registry.isEmergency());
    _;
  }

  constructor(Registry _registry, MiniMeToken _registryToken) public {
    registry = _registry;
    registryToken = _registryToken;
  }

  /**
   * @dev Creates new forwarder contract as registry entry
   * Transfers required deposit from creator into this contract
   * Approves new registry entry address to transfer deposit to itself
   * Adds new registry entry address into the registry

   * @param _creator Creator of registry entry
   * @return Address of a new registry entry forwarder contract
   */
  function createRegistryEntry(address _creator)
  notEmergency
  internal
  returns (address)
  {
    uint deposit = registry.db().getUIntValue(depositKey);
    address regEntry = new Forwarder1();
    require(registryToken.transferFrom(_creator, this, deposit));
    require(registryToken.approve(regEntry, deposit));
    registry.addRegistryEntry(regEntry);
    return regEntry;
  }

  /**
   * @dev Function called by MiniMeToken when somebody calls approveAndCall on it.
   * This way token can be transferred to a recipient in a single transaction together with execution
   * of additional logic

   * @param _from Sender of transaction approval
   * @param _amount Amount of approved tokens to transfer
   * @param _token Token that received the approval
   * @param _data Bytecode of a function and passed parameters, that should be called after token approval
   */
  function receiveApproval(
    address _from,
    uint256 _amount,
    address _token,
    bytes _data)
  public
  {
    _from;
    _amount;
    _token;
    require(address(this).call(_data));
  }
}