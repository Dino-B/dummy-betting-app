package com.test.mongodb.provision.provisioner;

/**
 * Declares provisioner type.
 */
public interface IProvisioner {

  /**
   * Generate items of certain type and then provision the database with them.
   */
  void generateAndProvision();
}
