(ns ethlance.shared.smart-contracts-dev)

  (def smart-contracts
    {:ds-guard {:name "DSGuard" :address "0x69424c639c2ee4be927973186fc9f6d7de07f1d1"} :minime-token-factory {:name "MiniMeTokenFactory" :address "0xc4b4865ce91ed92ade39c6c1dc6896cd84aaa55a"} :DNT {:name "District0xNetworkToken" :address "0xf9d1ffe3694047990ac9129cabf2c456dda3e6b5"} :district-registry-db {:name "EternalDb" :address "0x7e5bb39bc5c8387368455bdca5c22cb055f7612a"} :param-change-registry-db {:name "EternalDb" :address "0xab1eea176e2fe761cbe780f4e6b04bfe1e0db708"} :district-registry {:name "DistrictRegistry" :address "0xfc2f9d258364e9cd6fc1c2d675f82c2d39d87f02"} :district-registry-fwd {:name "MutableForwarder" :address "0xdd21698fc2719f498d6f651cc19221cc91075227" :forwards-to :district-registry} :param-change-registry {:name "ParamChangeRegistry" :address "0x2cc2cc05b6e1a6c3cfcdaf1cd6fd2cb8ed549624"} :param-change-registry-fwd {:name "MutableForwarder" :address "0x2edd149c2bfe85b2980bdb35c1869b6b3d2c4e6d" :forwards-to :param-change-registry} :power-factory {:name "PowerFactory" :address "0x6257e69520ce4a76ae5161c579ddcb308915fb44"} :stake-bank-factory {:name "StakeBankFactory" :address "0xd36b4a80e894deba183a559cb7009e857b474987"} :challenge-factory {:name "ChallengeFactory" :address "0x188305fc1f98bfd59f1d0b8236a3925c382e8d5b"} :district {:name "District" :address "0xf74dfc0e2308317f0ba5ab049d379109ef9cbcd3"} :param-change {:name "ParamChange" :address "0x5f49f882ae879b97ee92e2b742a7fefebba15f46"} :district-factory {:name "DistrictFactory" :address "0xbb9f0e1b0d2ee1f1c20bd2dc392192644499ae44"} :param-change-factory {:name "ParamChangeFactory" :address "0xf1d8e426c7a60e815c423ce8f7c46ddf749a5cbc"}})
